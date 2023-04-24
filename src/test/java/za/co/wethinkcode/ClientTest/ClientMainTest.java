package za.co.wethinkcode.ClientTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import za.co.wethinkcode.Client.ClientMain;

import static org.junit.jupiter.api.Assertions.assertEquals;

   class ClientMainTest {
        @Test
        void testGetInput() throws IOException {
            // Arrange
            String input = "yes";
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);
            String prompt = "Do you want to launch a robot? (yes/no): ";

            // Act
            ClientMain clientMain = new ClientMain();
            String result = clientMain.getInput(prompt);

            // Assert
            assertEquals("yes", result);
        }
   }

