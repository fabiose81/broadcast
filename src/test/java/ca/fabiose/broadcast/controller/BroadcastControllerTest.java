package ca.fabiose.broadcast.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import ca.fabiose.broadcast.domain.Broadcast;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BroadcastControllerTest {

    @Autowired
    private BroadcastController broadcastController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String URL;

    static final String BM_PROVIDER = "BM";
    static final String RANKING_RESOURCE = "/ranking/".concat(BM_PROVIDER);
    static final String FIND_RESOURCE = "/find/".concat(BM_PROVIDER);
    static final String RANKING_PATH = "/v1/broadcast".concat(RANKING_RESOURCE);
    static final String FIND_PATH = "/v1/broadcast".concat(FIND_RESOURCE);

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        URL = "http://localhost:"+port;
    }

    @Test
    public void contextLoads() {
        assertThat(broadcastController).isNotNull();
    }

    @Test
    public void when_ranking_should_pass() throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(RANKING_PATH)
                .build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));

        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, not(empty()));
        MatcherAssert.assertThat(broadcastRanking, hasSize(10));

        MatcherAssert.assertThat(broadcastRanking, hasItem(allOf(
                Matchers.<Broadcast>hasProperty("id"),
                Matchers.<Broadcast>hasProperty("provider"),
                Matchers.<Broadcast>hasProperty("title"),
                Matchers.<Broadcast>hasProperty("views")
        )));

        Broadcast broadcastPosition1 = broadcastRanking.get(0);
        Broadcast broadcastPosition10 = broadcastRanking.get(9);

        MatcherAssert.assertThat(broadcastPosition1.getViews(), greaterThan(broadcastPosition10.getViews()));

        MatcherAssert.assertThat(broadcastPosition1.getId(), comparesEqualTo("77c5bc7ced41d56ec0f54e0affbd1147"));
        MatcherAssert.assertThat(broadcastPosition1.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcastPosition1.getTitle(), comparesEqualTo("S.09 E.05 - The Big Bang Theory"));
        MatcherAssert.assertThat(broadcastPosition1.getViews(), comparesEqualTo(6634L));

        MatcherAssert.assertThat(broadcastPosition10.getId(), comparesEqualTo("00d25e9893ab0d655b186db48ec1517e"));
        MatcherAssert.assertThat(broadcastPosition10.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcastPosition10.getTitle(), comparesEqualTo("S.01 E.02 - Quantico"));
        MatcherAssert.assertThat(broadcastPosition10.getViews(), comparesEqualTo(2367L));
    }

    @Test
    public void when_ranking_return_empty_list() throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(RANKING_PATH.concat("onemorething"))
                .build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));

        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, empty());
    }

    @Test
    public void when_ranking_resource_not_found() {
        ResponseEntity<String> response = restTemplate.
                getForEntity(URL + "/v1/blablabla/", String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void when_find_page_1_should_return_list() throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(FIND_PATH)
                .queryParam("page", 1).build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));

        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, not(empty()));
        MatcherAssert.assertThat(broadcastRanking, hasSize(10));

        MatcherAssert.assertThat(broadcastRanking, hasItem(allOf(
                Matchers.<Broadcast>hasProperty("id"),
                Matchers.<Broadcast>hasProperty("provider"),
                Matchers.<Broadcast>hasProperty("title"),
                Matchers.<Broadcast>hasProperty("views")
        )));

        Broadcast broadcastPosition1 = broadcastRanking.get(0);
        Broadcast broadcastPosition10 = broadcastRanking.get(9);

        MatcherAssert.assertThat(broadcastPosition1.getViews(), greaterThan(broadcastPosition10.getViews()));

        MatcherAssert.assertThat(broadcastPosition1.getId(), comparesEqualTo("655d6fbf11ad26830dbd0ceff05837ff"));
        MatcherAssert.assertThat(broadcastPosition1.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcastPosition1.getTitle(), comparesEqualTo("2015 Ctv's Big Fall Preview"));
        MatcherAssert.assertThat(broadcastPosition1.getViews(), comparesEqualTo(302L));

        MatcherAssert.assertThat(broadcastPosition10.getId(), comparesEqualTo("7dbdb6395f34548fba246576ebd37c6e"));
        MatcherAssert.assertThat(broadcastPosition10.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcastPosition10.getTitle(), comparesEqualTo("Americans In Bed"));
        MatcherAssert.assertThat(broadcastPosition10.getViews(), comparesEqualTo(74L));
    }

    @Test
    public void when_find_page_2_should_return_list() throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(FIND_PATH)
                .queryParam("page", 2).build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));

        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, not(empty()));
        MatcherAssert.assertThat(broadcastRanking, hasSize(10));

        MatcherAssert.assertThat(broadcastRanking, hasItem(allOf(
                Matchers.<Broadcast>hasProperty("id"),
                Matchers.<Broadcast>hasProperty("provider"),
                Matchers.<Broadcast>hasProperty("title"),
                Matchers.<Broadcast>hasProperty("views")
        )));

        Broadcast broadcastPosition1 = broadcastRanking.get(0);
        Broadcast broadcastPosition10 = broadcastRanking.get(9);

        MatcherAssert.assertThat(broadcastPosition1.getViews(), greaterThan(broadcastPosition10.getViews()));

        MatcherAssert.assertThat(broadcastPosition1.getId(), comparesEqualTo("8ebc0236527f3f13f8ca59f42fd3b750"));
        MatcherAssert.assertThat(broadcastPosition1.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcastPosition1.getTitle(), comparesEqualTo("Amy Schumer"));
        MatcherAssert.assertThat(broadcastPosition1.getViews(), comparesEqualTo(107L));

        MatcherAssert.assertThat(broadcastPosition10.getId(), comparesEqualTo("e60c188b5feca84964468cfaf9618261"));
        MatcherAssert.assertThat(broadcastPosition10.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcastPosition10.getTitle(), comparesEqualTo("Bob Saget"));
        MatcherAssert.assertThat(broadcastPosition10.getViews(), comparesEqualTo(57L));
    }

    @Test
    public void when_find_last_page_should_return_list() throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(FIND_PATH)
                .queryParam("page", 148).build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));

        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, not(empty()));
        MatcherAssert.assertThat(broadcastRanking, hasSize(1));

        MatcherAssert.assertThat(broadcastRanking, hasItem(allOf(
                Matchers.<Broadcast>hasProperty("id"),
                Matchers.<Broadcast>hasProperty("provider"),
                Matchers.<Broadcast>hasProperty("title"),
                Matchers.<Broadcast>hasProperty("views")
        )));

        Broadcast broadcast = broadcastRanking.get(0);

        MatcherAssert.assertThat(broadcast.getId(), comparesEqualTo("8509e16d3e79eb515fbae8b82e22f1e7"));
        MatcherAssert.assertThat(broadcast.getProvider(), comparesEqualTo(BM_PROVIDER));
        MatcherAssert.assertThat(broadcast.getTitle(), comparesEqualTo("Witness Protection"));
        MatcherAssert.assertThat(broadcast.getViews(), comparesEqualTo(58L));
    }

    @Test
    void when_find_should_return_empty_list() throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(FIND_PATH.concat("onemorething"))
                .queryParam("page", 1).build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));
        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, empty());
    }

    @Test
    void when_find_should_return_empty_list2() throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .path(FIND_PATH)
                .queryParam("page", 200).build().toUri();

        ResponseEntity<String> response = restTemplate.
                getForEntity(uri, String.class);

        MatcherAssert.assertThat(response.getStatusCode(), comparesEqualTo(HttpStatus.OK));
        List<Broadcast> broadcastRanking = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(broadcastRanking, empty());
    }
}