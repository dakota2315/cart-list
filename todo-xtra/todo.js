const inputElement = document.getElementById("todo-input");
const listElement = document.getElementById("todo-list");

fetchTodos();
let todoItems = []

async function fetchTodos() {
    const resp = await fetch("http://localhost:8080/todos");
    todoItems = await resp.json();
    renderList();
}

async function addTodo() {
    const resp = await fetch("http://localhost:8080/todos", {
        method: "POST",
        body: JSON.stringify({ text: inputElement.value, isChecked: false }),
        headers: { "Content-Type": "application/json" }
    });
    const newItem = await resp.json();
    todoItems.push(newItem);
    renderList();
    inputElement.value = "";  
}

async function deleteItem(id) {
    await fetch(`http://localhost:8080/todos/${id}`, {
        method: "DELETE"
    })
    todoItems = todoItems.filter(todo => todo.id !== id)
    renderList();
}

function renderList() {
    listElement.innerHTML = (todoItems.map(todo => (
        `<li>
            ${todo.text}
            <button onclick="deleteItem(${todo.id})">Delete</button>
        </li>`
    ))).join("\n")
}