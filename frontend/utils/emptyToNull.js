export function emptyToNull(value) {
  if (value === null || value === "") {
    return null;
  }
  return value;
}
