package com.group2.theminimart.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.theminimart.dto.UserRegisterRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void registerUserTest() throws Exception {
    // Step 1: Build a POST request to api/auth/register
    UserRegisterRequestDto newUser = UserRegisterRequestDto.builder().username("firhat123").password("12345678")
        .build();

    String newUserAsJson = objectMapper.writeValueAsString(newUser);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newUserAsJson);

    // Step 2: Perform the request, get the response and assert
    mockMvc.perform(request).andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.username").value("firhat123"));
  }

  @Test
  public void invalidUsernameRegisterUserTest() throws Exception {
    // Step 1: Build a POST request to api/auth/register
    UserRegisterRequestDto newUser = UserRegisterRequestDto.builder().username("fi").password("12345678")
        .build();

    String newUserAsJson = objectMapper.writeValueAsString(newUser);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newUserAsJson);

    // Step 2: Perform the request, get the response and assert
    mockMvc.perform(request).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Username has to be at least 3 characters. "));
  }

  @Test
  public void invalidPasswordRegisterUserTest() throws Exception {
    // Step 1: Build a POST request to api/auth/register
    UserRegisterRequestDto newUser = UserRegisterRequestDto.builder().username("firhat123123").password("12")
        .build();

    String newUserAsJson = objectMapper.writeValueAsString(newUser);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newUserAsJson);

    // Step 2: Perform the request, get the response and assert
    mockMvc.perform(request).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Password has to be at least 8 characters. "));
  }

  @Test
  public void loginUserTest() throws Exception {
    // Step 1: Build a POST request to api/auth/register
    UserRegisterRequestDto firhat = UserRegisterRequestDto.builder().username("firhat").password("12345678")
        .build();

    String firhatAsJson = objectMapper.writeValueAsString(firhat);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(firhatAsJson);

    // Step 2: Perform the request, get the response and assert
    mockMvc.perform(request).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.username").value("firhat"));
  }

  @Test
  public void loginUserWrongUsernameTest() throws Exception {
    // Step 1: Build a POST request to api/auth/register
    UserRegisterRequestDto firhat = UserRegisterRequestDto.builder().username("firha").password("12345678")
        .build();

    String firhatAsJson = objectMapper.writeValueAsString(firhat);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(firhatAsJson);

    // Step 2: Perform the request, get the response and assert
    mockMvc.perform(request).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Invalid username or password."));
  }

  @Test
  public void loginUserWrongPasswordTest() throws Exception {
    // Step 1: Build a POST request to api/auth/register
    UserRegisterRequestDto firhat = UserRegisterRequestDto.builder().username("firhat").password("123456789")
        .build();

    String firhatAsJson = objectMapper.writeValueAsString(firhat);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(firhatAsJson);

    // Step 2: Perform the request, get the response and assert
    mockMvc.perform(request).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Invalid username or password."));
  }

}
