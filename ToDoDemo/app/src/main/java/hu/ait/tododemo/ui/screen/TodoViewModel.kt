package hu.ait.tododemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import hu.ait.tododemo.data.TodoItem
import hu.ait.tododemo.data.TodoPriority

class TodoViewModel : ViewModel() {

    private var _todoList =
        mutableStateListOf<TodoItem>()

    fun getAllToDoList(): List<TodoItem> {
        return _todoList
    }

    fun getAllTodoNum(): Int {
        return _todoList.size
    }

    fun getImportantTodoNum(): Int {
        return _todoList.count{it.priority == TodoPriority.HIGH}
    }

    fun addTodoList(todoItem: TodoItem) {
        _todoList.add(todoItem)
    }


    fun removeTodoItem(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun editTodoItem(originalTodo: TodoItem, editedTodo: TodoItem) {
        val index = _todoList.indexOf(originalTodo)
        _todoList[index] = editedTodo
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val index = _todoList.indexOf(todoItem)

        val newTodo = todoItem.copy(
            title = todoItem.title,
            description = todoItem.description,
            createDate = todoItem.createDate,
            priority = todoItem.priority,
            isDone = value
        )

        _todoList[index] = newTodo
    }

    fun clearAllTodos() {
        _todoList.clear()
    }

}