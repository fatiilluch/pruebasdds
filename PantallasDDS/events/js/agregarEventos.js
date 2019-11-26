function getImgProtocol() {
    let protocol = document.getElementById('protocol').value;

    let window = document.getElementById('output');
    if (protocol != "0") {
        window.src = "images/" + protocol + ".png";
    } else {
        window.src = "images/start.svg";
    }

    console.log(typeof protocol);
}