package ca.fabiose.broadcast.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import ca.fabiose.broadcast.domain.Broadcast;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class BroadcastServiceTest {

	@Autowired
	BroadcastService broadcastService;

	static final String ABM_PROVIDER = "ABM";

	@Test
	void when_rankingByProvider_should_return_list() {
		List<Broadcast> broadcastRanking = broadcastService.rankingByProvider(ABM_PROVIDER);

		assertThat(broadcastRanking, not(empty()));
		assertThat(broadcastRanking, hasSize(10));
		assertThat(broadcastRanking, hasItem(allOf(
				Matchers.<Broadcast>hasProperty("id"),
				Matchers.<Broadcast>hasProperty("provider"),
				Matchers.<Broadcast>hasProperty("title"),
				Matchers.<Broadcast>hasProperty("views")
		)));

		Broadcast broadcastPosition1 = broadcastRanking.get(0);
		Broadcast broadcastPosition10 = broadcastRanking.get(9);

		assertThat(broadcastPosition1.getViews(), greaterThan(broadcastPosition10.getViews()));

		assertThat(broadcastPosition1.getId(), comparesEqualTo("8408370c4097566f27c2a6ee01534407"));
		assertThat(broadcastPosition1.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcastPosition1.getTitle(), comparesEqualTo("Jupiter Ascending"));
		assertThat(broadcastPosition1.getViews(), comparesEqualTo(6815L));

		assertThat(broadcastPosition10.getId(), comparesEqualTo("00d355869e850e777a786405300b2847"));
		assertThat(broadcastPosition10.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcastPosition10.getTitle(), comparesEqualTo("S.02 E.31 Last Week Tonight With John Oliver"));
		assertThat(broadcastPosition10.getViews(), comparesEqualTo(746L));
	}

	@Test
	void when_rankingByProvider_should_return_empty_list() {
		MatcherAssert.assertThat(broadcastService.rankingByProvider(""), empty());
	}

	@Test
	void when_findByProvider_page_1_should_return_list() {
		List<Broadcast> broadcastRanking = broadcastService.findByProvider(ABM_PROVIDER,1);

		assertThat(broadcastRanking, not(empty()));
		assertThat(broadcastRanking, hasSize(10));
		assertThat(broadcastRanking, hasItem(allOf(
				Matchers.<Broadcast>hasProperty("id"),
				Matchers.<Broadcast>hasProperty("provider"),
				Matchers.<Broadcast>hasProperty("title"),
				Matchers.<Broadcast>hasProperty("views")
		)));

		Broadcast broadcastPosition1 = broadcastRanking.get(0);
		Broadcast broadcastPosition10 = broadcastRanking.get(9);

		assertThat(broadcastPosition1.getId(), comparesEqualTo("68fdf053679cb0894a424de6fa68b0c0"));
		assertThat(broadcastPosition1.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcastPosition1.getTitle(), comparesEqualTo("Amy Schumer Live At The Apollo"));
		assertThat(broadcastPosition1.getViews(), comparesEqualTo(4733L));

		assertThat(broadcastPosition10.getId(), comparesEqualTo("092f719d409927ecf20903714da00ff0"));
		assertThat(broadcastPosition10.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcastPosition10.getTitle(), comparesEqualTo("S.02 E.03 The Affair"));
		assertThat(broadcastPosition10.getViews(), comparesEqualTo(2688L));
	}

	@Test
	void when_findByProvider_page_2_should_return_list() {
		List<Broadcast> broadcastRanking = broadcastService.findByProvider(ABM_PROVIDER,2);

		assertThat(broadcastRanking, not(empty()));
		assertThat(broadcastRanking, hasSize(10));
		assertThat(broadcastRanking, hasItem(allOf(
				Matchers.<Broadcast>hasProperty("id"),
				Matchers.<Broadcast>hasProperty("provider"),
				Matchers.<Broadcast>hasProperty("title"),
				Matchers.<Broadcast>hasProperty("views")
		)));

		Broadcast broadcastPosition1 = broadcastRanking.get(0);
		Broadcast broadcastPosition10 = broadcastRanking.get(9);

		assertThat(broadcastPosition1.getId(), comparesEqualTo("dbae62587047b070bd374c47aa1d08cd"));
		assertThat(broadcastPosition1.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcastPosition1.getTitle(), comparesEqualTo("S.02 E.03 The Leftovers"));
		assertThat(broadcastPosition1.getViews(), comparesEqualTo(801L));

		assertThat(broadcastPosition10.getId(), comparesEqualTo("2e3cd72df5dd7937a64971134d350feb"));
		assertThat(broadcastPosition10.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcastPosition10.getTitle(), comparesEqualTo("The Trials Of Cate Mccall"));
		assertThat(broadcastPosition10.getViews(), comparesEqualTo(1810L));
	}

	@Test
	void when_findByProvider_last_page_should_return_list() {
		List<Broadcast> broadcastRanking = broadcastService.findByProvider(ABM_PROVIDER,3);

		assertThat(broadcastRanking, not(empty()));
		assertThat(broadcastRanking, hasSize(1));
		assertThat(broadcastRanking, hasItem(allOf(
				Matchers.<Broadcast>hasProperty("id"),
				Matchers.<Broadcast>hasProperty("provider"),
				Matchers.<Broadcast>hasProperty("title"),
				Matchers.<Broadcast>hasProperty("views")
		)));

		Broadcast broadcast = broadcastRanking.get(0);

		assertThat(broadcast.getId(), comparesEqualTo("419ac0040fa9e19ba0391c8f541ea4c9"));
		assertThat(broadcast.getProvider(), comparesEqualTo(ABM_PROVIDER));
		assertThat(broadcast.getTitle(), comparesEqualTo("The Wedding Wish"));
		assertThat(broadcast.getViews(), comparesEqualTo(65L));
	}

	@Test
	void when_findByProvider_should_return_empty_list() {
		MatcherAssert.assertThat(broadcastService.findByProvider("", 1), empty());
	}

	@Test
	void when_findByProvider_should_return_empty_list2() {
		MatcherAssert.assertThat(broadcastService.findByProvider(ABM_PROVIDER, 10), empty());
	}
}
