package ca.fabiose.broadcast.controller;

import ca.fabiose.broadcast.domain.Broadcast;
import ca.fabiose.broadcast.service.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/v1/broadcast")
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    @GetMapping("/ranking/{provider}")
    public List<Broadcast> ranking(@PathVariable String provider) {
        return  broadcastService.rankingByProvider(provider);
    }

    @GetMapping("/find/{provider}")
    public List<Broadcast> find(@PathVariable String provider, @RequestParam Integer page) {
        return  broadcastService.findByProvider(provider, page);
    }
}
