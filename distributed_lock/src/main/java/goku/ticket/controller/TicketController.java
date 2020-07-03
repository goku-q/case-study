package goku.ticket.controller;

import goku.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    /**
     * 添加ticketCount
     * @return
     */
    @RequestMapping("/add")
    public String addTicket(){
        return ticketService.saveTicket();
    }
    @RequestMapping("/get")
    public String getTicket(){
        return ticketService.getTicket();
    }
}
