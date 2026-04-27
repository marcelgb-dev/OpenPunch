/* static/js/scanner.js */

function onScanSuccess(decodedText) {
    window.location.href = `/punch?token=${encodeURIComponent(decodedText)}`;
}

let html5QrcodeScanner = new Html5QrcodeScanner(
    "reader", { fps: 10,
                qrbox: { width: 250, height: 250 },
                videoConstraints: {
                    facingMode: {exact: "environment"}
                }
    }
);

html5QrcodeScanner.render(onScanSuccess);

window.addEventListener('load', () => {
    const loadingMsg = document.getElementById('loading-message');
    const readerDiv = document.getElementById('reader');

    // Al inicio: ocultamos el lector de la librería
    readerDiv.style.display = 'none';

    setTimeout(() => {
        // 1. Ocultamos el mensaje de carga
        if (loadingMsg) loadingMsg.style.display = 'none';

        // 2. Mostramos el lector
        readerDiv.style.display = 'block';

        // 3. Ejecutamos el click automático
        const startButton = document.getElementById('reader__camera_permission_button')
            || document.querySelector('#reader__dashboard_section_csr button');
        if (startButton) startButton.click();

    }, 3000);
});