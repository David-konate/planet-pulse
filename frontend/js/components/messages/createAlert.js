export function createAlert(message, type) {
  const alert = document.createElement("div");
  alert.className = `alert alert-${type} alert-dismissible`;
  alert.setAttribute("role", "alert");

  const closeBtn = document.createElement("button");
  closeBtn.type = "button";
  closeBtn.className = "btn-close";
  closeBtn.setAttribute("aria-label", "Close");
  closeBtn.addEventListener("click", () => alert.remove());

  // A string passed to append() is inserted as plain text, never parsed as HTML
  alert.append(message, closeBtn);
  return alert;
}
