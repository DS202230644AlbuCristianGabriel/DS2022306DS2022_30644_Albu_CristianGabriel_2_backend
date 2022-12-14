package com.company.my_app;

import com.company.my_app.registred_smart_energy.RegistredSmartEnergyDTO;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.sql.Timestamp;

public class Send {
    void send(String in) throws FileNotFoundException, InterruptedException {
        String createPersonUrl = "https://backendassignment2.azurewebsites.net/api/registredSmartEnergy";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject q = new JSONObject(in);
        JSONObject queue = (JSONObject) q.get("consumption");
        HttpEntity<String> request = new HttpEntity<>(queue.toString(), headers);
        RegistredSmartEnergyDTO registredSmartEnergyDTO = new RegistredSmartEnergyDTO();
        registredSmartEnergyDTO.setTimestamp(Timestamp.valueOf((String) queue.get("timestamp")));
        registredSmartEnergyDTO.setMeasurement_value((Double) queue.get("measurement_value"));
        registredSmartEnergyDTO.setDeviceRegistredSmartEnergy((Integer) queue.get("device_id"));
        restTemplate.postForObject(createPersonUrl, registredSmartEnergyDTO, String.class);
    }
}
