/**
 * @description Accept a string and capitalise the first word. Accounts for empty string edge case.
 * @param {string} str it accept a string
 * @returns {string} return a string and capitalise **only** the first character
 * @example
 * capitalizeFirstLetter("hello world") = "Hello world"
 * capitalizeFirstLetter("this is a test message.") = "This is a test message."
 */
export default function capitalizeFirstLetter(str) {
  return str ? str[0].toUpperCase() + str.slice(1) : "";
}
