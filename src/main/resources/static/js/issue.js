function setProperty(url, element) {

    const issueIdInput = document.getElementById("id");
    if (!issueIdInput) {
        return;
    }

    const body = {};
    body[element.name] = element.value;

    axios.patch(url + '/' + issueIdInput.value, body)
        .then(response => {
            console.log(response);
        }).catch(error => {
        console.log(error);
    });
}