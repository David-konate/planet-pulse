const messages = {
  valueMissing: "This field is required",
  typeMismatch: "The format is invalid",
  tooShort: "The text is too short",
  tooLong: "The text is too long",
  patternMismatch: "The format is invalid",
};

function isValidDate(dateString) {
  // split in 3 parts then convert in number
  const [month, day, year] = dateString.split("-").map(Number);

  // constrcut a date -1 because for JS months from 0 to 11
  const date = new Date(year, month - 1, day);
  return date.getFullYear() === year && date.getMonth() === month - 1 && date.getDate() === day;
}

function validateField(field) {
  field.setCustomValidity("");

  for (const type in messages) {
    if (field.validity[type]) {
      field.setCustomValidity(messages[type]);
      break;
    }
  }
  // check if the date is valid
  if (field.id === "note-date") {
    if (field.value && !isValidDate(field.value)) {
      field.setCustomValidity("Please enter a valid date")
    }
  }
}



// when the user types in a field, validate it
document.addEventListener("input", function (event) {
  validateField(event.target);
});

// when the user clicks Submit with an invalid field
document.addEventListener("invalid", function (event) {
  validateField(event.target);
}, true);