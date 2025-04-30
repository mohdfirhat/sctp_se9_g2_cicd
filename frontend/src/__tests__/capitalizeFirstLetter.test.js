import capitalizeFirstLetter from "../../src/utils/capitalizeFirstLetter";

describe("capitalizeFirstLetter", () => {
  //it() describes a single test
  it("should return empty string when parameter is empty string", () => {
    const str = "";
    const result = capitalizeFirstLetter(str);
    expect(result).toStrictEqual("");
  });

  it("should capitalised first letter for all lowercase string", () => {
    const str = "hello world";
    const result = capitalizeFirstLetter(str);
    expect(result).toStrictEqual("Hello world");
  });

  it("should capitalised first letter for string with uppercase after first letter", () => {
    const str = "hello World";
    const result = capitalizeFirstLetter(str);
    expect(result).toStrictEqual("Hello World");
  });

  it("should capitalised first letter for string with uppercase first letter", () => {
    const str = "Hello world";
    const result = capitalizeFirstLetter(str);
    expect(result).toStrictEqual("Hello world");
  });
});
