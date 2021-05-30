package ca.fabiose.broadcast.service;

import ca.fabiose.broadcast.configuration.BroadcastConfig;
import ca.fabiose.broadcast.domain.Broadcast;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BroadcastService {

    private ApplicationContext context = new AnnotationConfigApplicationContext(BroadcastConfig.class);
    private List<Broadcast> list = context.getBean(List.class);
    public final int LIMIT_PAGE = 10;

    public List<Broadcast> rankingByProvider(String provider) {
        return list.stream()
                .filter(b -> b.getProvider().equalsIgnoreCase(provider))
                .sorted(Comparator.comparing(Broadcast::getViews).reversed())
                .limit(LIMIT_PAGE)
                .collect(Collectors.toList());
    }

    public List<Broadcast> findByProvider(String provider, int page) {
        return list.stream()
                .filter(b -> b.getProvider().equalsIgnoreCase(provider))
                .sorted(Comparator.comparing(Broadcast::getTitle))
                .skip((page - 1) * LIMIT_PAGE)
                .limit(LIMIT_PAGE)
                .collect(Collectors.toList());
    }
}
