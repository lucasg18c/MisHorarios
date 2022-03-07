const url = "http://192.168.0.136:8080/api";

document.getElementById("btnIniciar").addEventListener("click", () => {
    let email = document.getElementById("txtEmail").value;
    let passwd = document.getElementById("txtPasswd").value;

    let usuario = {
        "email": email,
        "passwd": passwd
    }

    fetch(`${url}/users/init`,{
        method: "POST",
        body: JSON.stringify(usuario),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json())
    .then(json => {

        if (json.id == -1) {
            alert("Los datos ingresados son incorrectos.")
            return
        }

        localStorage.setItem("user", JSON.stringify(json))
        this.location = "horarios.html"
})
})