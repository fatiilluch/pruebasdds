function checkPass() {
    let passToCheck = document.getElementById("pass").value;
    let passCheck = document.getElementById("passCheck").value;
    if (passToCheck != passCheck) {
        alert("Las contraseñas no coinciden");
    }

}