export function ErrorMessage(message) {
    this.feedback.innerHTML = /*html*/ `
    <div class="alert alert-danger alert-dismissible" role="alert">
      ${message}
      <button type="button" class="btn-close" id="close-feedback" aria-label="Close"></button>
    </div>`;
}