/**
 * @description Accept a decimal point and display it as "$xxx.xx"
 * @param
 * {number} floatPrice it can be any number with or without decimal
 * @returns
 * {string}
 * @example
 * displayCost(12.552) = "$12.55"
 * displayCost(12) = "$12.00"
 */
export function displayCost(floatPrice) {
  return `$${floatPrice.toFixed(2)}`;
}
