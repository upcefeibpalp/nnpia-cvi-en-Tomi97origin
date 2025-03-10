package cz.upce.fei.nnpia.st67084.nnpiacv.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import cz.upce.fei.nnpia.st67084.nnpiacv.dto.UserRequestDTO;
import cz.upce.fei.nnpia.st67084.nnpiacv.dto.UserResponseDTO;
import cz.upce.fei.nnpia.st67084.nnpiacv.repository.UserRepository;
import cz.upce.fei.nnpia.st67084.nnpiacv.services.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService; // Inject JwtService

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    private User testUser;
    private String jwtToken;
    @Autowired
    private ProjectInfoProperties projectInfoProperties;

    @BeforeEach
    void setUp() {
        // Vytvoření testovacího uživatele v H2 databázi před každým testem
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password")); // Encode password for security
        testUser = userRepository.save(testUser);

        // Generate JWT token for the test user
        jwtToken = jwtService.generateToken(testUser); // Assuming generateToken takes User as argument
    }

    @AfterEach
    void tearDown() {
        // Smazání všech záznamů z databáze po každém testu
        userRepository.delete(testUser);
    }

    @Test
    void findUser() throws Exception {
        // Testování GET endpointu /api/v1/users/{id} with JWT token

        mockMvc.perform(get("/api/v1/users/{id}", testUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)) // Add Authorization header
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Testování, že vrací JSON
                .andExpect(jsonPath("$.id", is(testUser.getId().intValue()))) // Testování ID
                .andExpect(jsonPath("$.email", is(testUser.getEmail()))); // Testování emailu*/
    }

    @Test
    void findUser_checkReturnType() throws Exception {
        String responseContent = mockMvc.perform(get("/api/v1/users/{id}", testUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)) // Add Authorization header
                .andExpect(status().isFound())
                .andReturn().getResponse().getContentAsString();

        UserResponseDTO userResponseDTO = objectMapper.readValue(responseContent, UserResponseDTO.class);
        assertEquals(UserResponseDTO.class, userResponseDTO.getClass()); // Kontrola, zda je návratový typ UserResponseDTO
        assertEquals(testUser.getId(), userResponseDTO.getId()); // Kontrola ID pomocí DTO
        assertEquals("test@example.com", userResponseDTO.getEmail()); // Kontrola email pomocí DTO
    }

    @Test
    void findUser_checkExpectedValues() throws Exception {
        // Testování, že vracený uživatel obsahuje očekávané hodnoty with JWT token
        mockMvc.perform(get("/api/v1/users/{id}", testUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)) // Add Authorization header
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void findUser_notFound() throws Exception {
        // Testování případu, kdy uživatel neexistuje - očekáváme 404 NOT_FOUND with JWT token
        mockMvc.perform(get("/api/v1/users/{id}", 99999L) // ID, které s největší pravděpodobností neexistuje
                        .header("Authorization", "Bearer " + jwtToken)) // Add Authorization header
                .andExpect(status().isNotFound());
    }

    @Test
    void findUser_forbidden() throws Exception {
        // Testování případu, kdy chybí JWT token - očekáváme 403
        mockMvc.perform(get("/api/v1/users/{id}", testUser.getId())) // No Authorization header
                .andExpect(status().isForbidden()); // Expect 403
    }

    @Test // ADDED
    void createUser() throws Exception {
        // Testování POST endpointu /api/v1/users for creating a user

        UserRequestDTO userRequestDTO = new UserRequestDTO(); // Create UserRequestDTO for request body
        userRequestDTO.setEmail("new.user@example.com");
        userRequestDTO.setPassword("password");

        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", "Bearer " + jwtToken) // Add JWT token
                        .contentType(MediaType.APPLICATION_JSON) // Set Content-Type header
                        .content(objectMapper.writeValueAsString(userRequestDTO))) // Serialize UserRequestDTO to JSON
                .andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect JSON response
                .andExpect(jsonPath("$.id").exists()) // Check if ID is generated
                .andExpect(jsonPath("$.email", is(userRequestDTO.getEmail()))); // Check email
    }

    @Test // ADDED
    void createUser_checkReturnType() throws Exception {
        // Testování návratového typu for createUser endpoint

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("another.user@example.com");
        userRequestDTO.setPassword("password");

        String responseContent = mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserResponseDTO userResponseDTO = objectMapper.readValue(responseContent, UserResponseDTO.class);
        assertEquals(UserResponseDTO.class, userResponseDTO.getClass()); // Check return type
        assertEquals("another.user@example.com", userResponseDTO.getEmail()); // Check email from DTO
    }

    @Test // ADDED
    void createUser_checkExpectedValues() throws Exception {
        // Testování návratových hodnot for createUser endpoint

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("expected.values@example.com");
        userRequestDTO.setPassword("password");

        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("expected.values@example.com"))); // Check expected email
    }

    @Test // ADDED
    void createUser_unauthorized() throws Exception {
        // Testování případu, kdy chybí JWT token for createUser - očekáváme 401 UNAUTHORIZED

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("unauthorized.user@example.com");
        userRequestDTO.setPassword("password");

        mockMvc.perform(post("/api/v1/users") // No Authorization header
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isForbidden()); // Expect 401 Unauthorized
    }

    @Test
    void createUser_conflict() throws Exception {
        // Testování POST endpointu /api/v1/users s existujícím emailem - očekáváme 409 Conflict

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("test@example.com"); // Použijeme email existujícího uživatele
        userRequestDTO.setPassword("password");

        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isConflict()); // Expect 409 Conflict
    }
}