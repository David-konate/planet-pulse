const messages = {
  valueMissing: "This field is required",
  typeMismatch: "The format is invalid",
  tooShort: "The text is too short",
  tooLong: "The text is too long",
};

function validateField(field) {
  field.setCustomValidity("");

  for (const type in messages) {
    if (field.validity[type]) {
      field.setCustomValidity(messages[type]);
      break;
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