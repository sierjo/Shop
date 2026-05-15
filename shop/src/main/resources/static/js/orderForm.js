function handleAddressSelection() {
    let select = document.getElementById('selectedAddressId');
    let inputs = document.querySelectorAll('.new-addr-input');
    let checkboxWrapper = document.getElementById('saveAddressCheckboxWrapper');
    let checkbox = document.getElementById('saveNewAddress');

    // НОВОЕ: Если списка адресов нет на странице (у пользователя 0 адресов)
    if (!select) {
        inputs.forEach(input => input.setAttribute('required', 'required'));
        return;
    }
// Очищаем ошибку чекбокса при любом переключении списка
    clearCheckboxError();
    // Если список есть и в нем выбран сохраненный адрес
    if (select.value !== "") {
        inputs.forEach(input => input.removeAttribute('required'));

        let selectedOption = select.options[select.selectedIndex];
        document.getElementById('firstName').value = selectedOption.getAttribute('data-name') || '';
        document.getElementById('lastName').value = selectedOption.getAttribute('data-surname') || '';
        document.getElementById('street').value = selectedOption.getAttribute('data-street') || '';
        document.getElementById('buildingNo').value = selectedOption.getAttribute('data-building') || '';
        document.getElementById('aptNo').value = selectedOption.getAttribute('data-apt') || '';
        document.getElementById('zipCode').value = selectedOption.getAttribute('data-zip') || '';
        document.getElementById('city').value = selectedOption.getAttribute('data-city') || '';

        let countrySelect = document.getElementById('country');
        let countryVal = selectedOption.getAttribute('data-country');
        if (countrySelect && countryVal) {
            countrySelect.value = countryVal;
        }

        if (checkboxWrapper) checkboxWrapper.style.display = 'none';
        if (checkbox) checkbox.checked = false;

    } else {
        // Если выбран Nowy adres
        inputs.forEach(input => input.setAttribute('required', 'required'));
        clearFormFields();

        if (checkboxWrapper) checkboxWrapper.style.display = 'block';
    }
}

function clearFormFields() {
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('phoneNumber').value = '';
    document.getElementById('street').value = '';
    document.getElementById('buildingNo').value = '';
    document.getElementById('aptNo').value = '';
    document.getElementById('zipCode').value = '';
    document.getElementById('city').value = '';
}


// Функция для сброса красной ошибки чекбокса
function clearCheckboxError() {
    let checkboxLabel = document.querySelector('label[for="saveNewAddress"]');
    let errorMsg = document.getElementById('checkboxErrorMsg');
    if (checkboxLabel) checkboxLabel.style.color = '#b78a76'; // Возврат старого цвет
    if (errorMsg) errorMsg.remove();
}

window.onload = function () {
    handleAddressSelection();

    // Если ставит галочку, ошибка пропадает
    let checkbox = document.getElementById('saveNewAddress');
    if (checkbox) {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                clearCheckboxError();
            }
        });
    }
};


function validateAndSubmitForm(event) {
    let select = document.getElementById('selectedAddressId');
    let checkbox = document.getElementById('saveNewAddress');

    // Если выбран "-- Nowy adres --", галочка обязательна
    if (select && select.value === "") {
        if (checkbox && !checkbox.checked) {
            event.preventDefault(); // Остановка отправки формы

            let checkboxLabel = document.querySelector('label[for="saveNewAddress"]');

            // красный текст
            if (checkboxLabel) {
                checkboxLabel.style.color = 'red';
            }

            // текст ошибки
            if (!document.getElementById('checkboxErrorMsg')) {
                let errorMsg = document.createElement('span');
                errorMsg.id = 'checkboxErrorMsg';
                errorMsg.style.color = 'red';
                errorMsg.style.fontSize = '12px';
                errorMsg.style.marginLeft = '10px';
                errorMsg.innerText = '(Zaznacz pole, aby zapisać nowy adres, lub wybierz istniejący z listy)';

                if (checkboxLabel) {
                    checkboxLabel.parentNode.appendChild(errorMsg);
                }
            }
            return; // Прерывание функции, пользователь должен поставить галочку
        }
    }
    // Проверяем если выбран адрес из списка
    if (select && select.value !== "") {
        let selectedOption = select.options[select.selectedIndex];

        // Достаем оригинальные данные (которые были при загрузке страницы)
        let origName = selectedOption.getAttribute('data-name') || '';
        let origSurname = selectedOption.getAttribute('data-surname') || '';
        let origStreet = selectedOption.getAttribute('data-street') || '';
        let origBuilding = selectedOption.getAttribute('data-building') || '';
        let origApt = selectedOption.getAttribute('data-apt') || '';
        let origZip = selectedOption.getAttribute('data-zip') || '';
        let origCity = selectedOption.getAttribute('data-city') || '';

        // Достаем текущие данные которые ввел пользователь
        let currName = document.getElementById('firstName').value.trim();
        let currSurname = document.getElementById('lastName').value.trim();
        let currStreet = document.getElementById('street').value.trim();
        let currBuilding = document.getElementById('buildingNo').value.trim();
        let currApt = document.getElementById('aptNo').value.trim();
        let currZip = document.getElementById('zipCode').value.trim();
        let currCity = document.getElementById('city').value.trim();

        // Сравниваем если хоть одно поле не совпадает, значит были изменения
        let isChanged = (origName !== currName) ||
            (origSurname !== currSurname) ||
            (origStreet !== currStreet) ||
            (origBuilding !== currBuilding) ||
            (origApt !== currApt) ||
            (origZip !== currZip) ||
            (origCity !== currCity);

        if (isChanged) {
            // ОСТАНАВЛИВАЕМ стандартную отправку формы
            event.preventDefault();

            // Дополнительное окно с выбором сохранить как новый или переписать старый
            let message = "Zmieniono dane adresu.\n\nCzy chcesz zapisać to jako NOWY adres?\n\n[OK] = Zapisz jako NOWY adres\n[Anuluj] = Zaktualizuj i nadpisz obecny";
            let saveAsNew = confirm(message);

            let form = document.getElementById('checkoutForm');

            if (saveAsNew) {
                // Если нажал "OK" -> Сохранить как НОВЫЙ
                select.value = ""; // Очищаем ID, чтобы бэкенд подумал, что это новый адрес

                // Проверяем, есть ли чекбокс, и ставим галочку (чтобы бэкенд его сохранил)
                let saveCheckbox = document.getElementById('saveNewAddress');
                if (saveCheckbox) {
                    saveCheckbox.checked = true;
                } else {
                    // Если чекбокса нет, создаем скрытое поле
                    let hiddenSave = document.createElement('input');
                    hiddenSave.type = 'hidden';
                    hiddenSave.name = 'saveNewAddress';
                    hiddenSave.value = 'true';
                    form.appendChild(hiddenSave);
                }
            } else {
                // Если нажал "Отмена" -> ПЕРЕЗАПИСАТЬ старый адрес
                // Создаем скрытое поле overwriteExisting = true
                let hiddenOverwrite = document.createElement('input');
                hiddenOverwrite.type = 'hidden';
                hiddenOverwrite.name = 'overwriteExisting';
                hiddenOverwrite.value = 'true';
                form.appendChild(hiddenOverwrite);
            }

            // отправка формы
            form.submit();
        }
    }
}