export function init(root) {
  const currentPage = location.pathname.split("/").pop() || "index.html";

  root.querySelectorAll(".nav-link[href]").forEach((link) => {
    if (link.getAttribute("href") === currentPage) {
      link.classList.add("active");
      link.setAttribute("aria-current", "page");
    }
  });
}
