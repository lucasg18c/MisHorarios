const url = "http://192.168.0.136:8080/api";

let usuario = JSON.parse(localStorage.getItem("user"));

document.getElementById("lblSaludo").innerHTML = `Bienvenido ${usuario.name}`;

fetch(`${url}/courses/user/${usuario.id}`)
.then(res => res.json())
.then(json => {

    let cursos = "<ul>"

    json.forEach(curso => {
        cursos += `<li>${curso.name}</li>`
    });

    document.getElementById("lista_cursos").innerHTML = cursos + "</ul>"
})