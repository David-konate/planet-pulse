export function setRangeMessages(input, messages = {}) {
  const { underflow = "", overflow = "" } = messages;

  function onInputChange() {
    const { rangeUnderflow, rangeOverflow } = input.validity;

    if (rangeUnderflow) {
      input.setCustomValidity(underflow);
    } else if (rangeOverflow) {
      input.setCustomValidity(overflow);
    } else {
      input.setCustomValidity("");
    }
  }

  input.addEventListener("input", onInputChange);
}
