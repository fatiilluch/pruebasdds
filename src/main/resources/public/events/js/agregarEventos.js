function getImgProtocol() {
    let protocol = document.getElementById('protocol').value;

    let window = document.getElementById('output');
    if (protocol != "0") {
        window.src = "../events/images/" + protocol + ".png";
    } else {
        window.src = "images/start.svg";
    }

    console.log(typeof protocol);
}

function changeBackgroundOutPut() {

    let colorOutput = document.getElementById('color').value;
    let protocol = document.getElementById('protocol').value;

    let window = document.getElementById('output');
    if (colorOutput != "black") {
        window.src = "../events/images/" + protocol + ".png";
        window.style.backgroundColor = colorOutput;

    } else {

        window.src = "../events/images/" + protocol + "Black.png";
        window.style.backgroundColor = colorOutput;
        if (protocol == "formal") {
            window.style.backgroundColor = "white";
        }
    }


    console.log(window.src);
}