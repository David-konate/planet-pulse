const MAX_INPUT_LENGTH = 200; 
const MAX_SLUG_LENGTH = 80; 

export function slugify(text) {
  return String(text)
    .normalize("NFD") // sépare les lettres de leurs accents (Å → A + ̊)
    .replace(/[\u0300-\u036f]/g, "") // supprime les accents
    .replace(/ß/g, "ss") // caractères qui ne se décomposent pas
    .replace(/[æÆ]/g, "ae")
    .replace(/[øØ]/g, "o")
    .replace(/[œŒ]/g, "oe")
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, "-") // tout ce qui n'est pas alphanumérique devient "-"
    .replace(/^-+|-+$/g, ""); // retire les "-" au début et à la fin
}

export function isValidSlug(value) {
  return (
    typeof value === "string" &&
    value.length > 0 &&
    value.length <= MAX_SLUG_LENGTH &&
    /^[a-z0-9]+(?:-[a-z0-9]+)*$/.test(value)
  );
}
