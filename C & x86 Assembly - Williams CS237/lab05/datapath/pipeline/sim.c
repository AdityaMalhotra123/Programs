#include <stdio.h>
#include <stdlib.h>
#include "isa.h"
#include "registers.h"
#include "parser.h"
#include "sim.h"
#include "instruction.h"

// Given a pointer to a pipeline array, checks if a control instruction
// has been executed. Returns 1 if it hasn't, 0 it it has.
int isControlHazard(Instruction **pipeline) {
  // Check if there is a control instruction that hasn't passed
  // Program Counter update stage.
  for (int i = 0; i <= PIPE_KNOW_NEXTPC; i++) {
    if (pipeline[i] != NULL) {
      if (isControlInstruction(pipeline[i]->name) == 1) {
        return 1;
      }
    }
  }
  return 0;
}

// Given the current stage # and a pointer to a pipeline array, 
// checks if a source register of the current stage is yet to undergo writeback
// due to a pervious instruction.
// Returns 1 if true, 0 if false.
int isRAWHazard(int stage, Instruction **pipeline) {
  RegID srcRegisters[MAX_SRC_REGS];
  int numSrcRegisters = getSrcRegisters(pipeline[stage], srcRegisters);
  
  for (int i = stage + 1; i < NUM_STAGES; i++) {
    if (pipeline[i] != NULL) {
      RegID destRegisters[MAX_DEST_REGS];
      int numDestRegisters = getDestRegisters(pipeline[i], destRegisters);

      // Look for overlap between current stage srcRegisters 
      // and later stage destRegisters
      for (int j = 0; j < numDestRegisters; j++) {
        for (int k = 0; k < numSrcRegisters; k++) {
          if (destRegisters[j] == srcRegisters[k]) {
            return 1;
          }
        }
      }
    }
  }
  
  return 0;
}

// Prints usage information
void printUsage()
{
  printf("Usage: sim <tracefile> <# instructions>\n");
}

// Given a Y86-64 tracefile and the number of instructions to simulate in
// the trace file, simulates executing those instructions on an ideal
// pipeline, a stalling pipeline, and a data forwarding pipeline and prints
// out the completion times of those instructions on each pipeline.
int main(int argc, char *argv[])
{
  if(argc < 3){
    printUsage();
    exit(EXIT_FAILURE);
  }

  // Make sure the number of instructions specified is a positive integer
  int numInstrs = atoi(argv[2]);
  if(numInstrs < 1){
    printUsage();
    exit(EXIT_FAILURE);
  }

  // Get the set of instructions from the trace file
  Instruction *instrs = readInstructions(argv[1], numInstrs);


  // Execute and print the results of the ideal pipeline
  InstructionCompletion * noHazardTimes = executeNoHazardPipeline(instrs,
								  numInstrs);
  printCompletionTimes("No hazards", noHazardTimes, numInstrs);
  free(noHazardTimes);

  // Execute and print the results of the stalling pipeline  
  InstructionCompletion * stallTimes = executeStallPipeline(instrs,
							    numInstrs);
  printCompletionTimes("\nStall on hazards", stallTimes, numInstrs);
  free(stallTimes);

}

// Given an array of instructions, execute the specified number of
// instructions on the pipeline and return an array of InstructionCompletion
// instances, one for each of the numInstrs that indicates the instruction's
// completion time.  This pipeline is ideal in that no instructions stall.
InstructionCompletion * executeNoHazardPipeline(Instruction *instrs,
						int numInstrs)
{
  // Create completion time array
  InstructionCompletion *completeTimes =
    (InstructionCompletion*)malloc(numInstrs * sizeof(InstructionCompletion));

  // Initialize an empty pipeline of NUM_STAGES.  Each pipeline stage
  // stores a pointer to the Instruction in that stage or NULL if no
  // instruction is currently in that stage.
  Instruction *pipeline[NUM_STAGES];
  for(int i = 0; i < NUM_STAGES; i++){
    pipeline[i] = NULL;
  }

  // Simulate the pipeline
  int time = 0;
  int completedCount = 0;
  int enteredCount = 0;
  
  // While not all instructions have completed execution
  while(enteredCount < numInstrs || !isPipelineEmpty(pipeline)){
    
    // Move instructions already in the pipeline one stage forward starting at
    // end of pipeline and moving forward to first pipeline stage
    for(int i = NUM_STAGES-1; i >= 0; i--){      
      if(pipeline[i] != NULL){
	// If the pipeline stage is not empty
	
	if(i == PIPE_WRITE_REG_STAGE){
	  // If the pipeline stage is the last stage where we write to
	  // registers, create an InstructionCompletion entry for the
	  // instruction
	  completeTimes[completedCount].instr = pipeline[i];
	  completeTimes[completedCount].completionTime = time;
	  completedCount++;
	  pipeline[i] = NULL;
	}
	else{
	  // If the pipeline stage is not the last stage, just advance
	  // the instruction one stage.  Since we don't recognize hazards
	  // and consequently don't stall instructions, there is no reason
	  // to check if the next pipeline stage is unoccupied before
	  // advancing.
	  pipeline[i+1] = pipeline[i];
	  pipeline[i] = NULL;
	}
      }
    }
    
    if(enteredCount < numInstrs){
      // This handles the insertion of instructions into the first
      // stage of the pipeline.  If there are instructions that
      // haven't entered the pipeline yet, insert the next instruction
      // into the pipeline's first stage.
      pipeline[PIPE_ENTER_STAGE] =  &instrs[enteredCount];
      enteredCount++;
    }

    // Advance time
    time++;
  }

  // Return the array of InstructionCompletion instances
  return completeTimes;
}

// Given an array of instructions, execute the specified number of
// instructions on the pipeline and return an array of InstructionCompletion
// instances, one for each of the numInstrs that indicates the instruction's
// completion time.  This pipeline stalls instructions waiting for their
// source operand registers to be written by an instruction already in the
// pipeline until that instruction has been to the register file.  It also
// stalls on control instructions until the target destination is know.
InstructionCompletion * executeStallPipeline(Instruction *instrs,
						int numInstrs)
{
  // Create completion time array
  InstructionCompletion *completeTimes =
    (InstructionCompletion*)malloc(numInstrs * sizeof(InstructionCompletion));

  // Initialize an empty pipeline of NUM_STAGES.  Each pipeline stage
  // stores a pointer to the Instruction in that stage or NULL if no
  // instruction is currently in that stage.
  Instruction *pipeline[NUM_STAGES];
  for(int i = 0; i < NUM_STAGES; i++){
    pipeline[i] = NULL;
  }

  // Simulate the pipeline
  int time = 0;
  int completedCount = 0;
  int enteredCount = 0;
  
  // While not all instructions have completed execution
  while(enteredCount < numInstrs || !isPipelineEmpty(pipeline)){
    // Move instructions already in the pipeline one stage forward starting at
    // end of pipeline and moving forward to first pipeline stage
    for(int i = NUM_STAGES-1; i >= 0; i--){      
      if(pipeline[i] != NULL){
	// If the pipeline stage is not empty

	if(i == PIPE_WRITE_REG_STAGE){
	  // If the pipeline stage is the last stage where we write to
	  // registers, create an InstructionCompletion entry for the
	  // instruction
	  completeTimes[completedCount].instr = pipeline[i];
	  completeTimes[completedCount].completionTime = time;
	  completedCount++;
	  pipeline[i] = NULL;
	  continue;
	}
	else if(i == PIPE_READ_REG_STAGE) {
    // If the pipeline stage is the stage where we read from instruction
    // registers, stall if any of them still need to be modified due to a 
    // previous instruction.
	  if (isRAWHazard(i, pipeline) == 1) {
	    continue;
	  }
	}
	
	// If the pipeline stage is not the last stage
	// and the next stage is free, advance
	// the instruction one stage, otherwise do nothing (stall).
	if(pipeline[i+1] == NULL) {
	  pipeline[i+1] = pipeline[i];
	  pipeline[i] = NULL;
	}
      }
    }

    if (isControlHazard(pipeline) == 0) {
      if(enteredCount < numInstrs){
	// This handles the insertion of instructions into the first
	// stage of the pipeline.  If there are instructions that
	// haven't entered the pipeline yet, insert the next instruction
	// into the pipeline's first stage, checking also that the
	// entry stage of the pipeline is currently free. If the pipeline
	// entry stage is occupied, do nothing (stall).
	if(pipeline[PIPE_ENTER_STAGE] == NULL) {
	  pipeline[PIPE_ENTER_STAGE] =  &instrs[enteredCount];
	  enteredCount++;
	}
      }
    }

    // Advance time
    time++;
  }

  // Return the array of InstructionCompletion instances
  return completeTimes;
}

// Returns 1 if all pipeline stages are empty.  Returns 0 otherwise.
int isPipelineEmpty(Instruction *pipeline[])
{
  for(int i = 0; i < NUM_STAGES; i++){
    if(pipeline[i] != NULL){
      return 0;
    }
  }
  return 1;
}

// For a specified pipeline, print out the specified number of instructions
// and their completion times to stdout
void printCompletionTimes(char *pipelineName,
			  InstructionCompletion *instrTimes, int numInstrs)
{
  if(instrTimes == NULL)
    return;
  
  printf("\n%s:\n", pipelineName);

  printf("Instr# \t Addr \t Instruction \t\t\t\tCompletion Time\n");  
  printf("------ \t ---- \t ----------- \t\t\t\t---------------\n");
  for(int i = 0; i < numInstrs; i++){
    char buffer[40];
    char *asmPtr = getInstructionAssembly(instrTimes[i].instr);
    padString(buffer, asmPtr, 40);

    printf("%d:   \t %s \t%d\n", (i+1),   
	   buffer,
	   instrTimes[i].completionTime);
    free(asmPtr);
  }
}

