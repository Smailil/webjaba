package ru.msu.com.webprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.com.webprak.DAO.*;
import ru.msu.com.webprak.DAO.impl.*;
import ru.msu.com.webprak.models.*;

import java.util.List;

@Controller
public class DisbursementPolicyController {
    @Autowired
    private final DisbursementPolicyDAO disbursementPolicyDAO = new DisbursementPolicyDAOImpl();

    @GetMapping("/disbursementPolicies")
    public String getProjects(Model model) {
        List<DisbursementPolicy> disbursementPolicy = (List<DisbursementPolicy>) disbursementPolicyDAO.getAll();
        model.addAttribute("disbursementPolicy", disbursementPolicy);
        return "disbursementPolicies";
    }

    @PostMapping("/addDisbursementPolicy")
    public String addDisbursementPolicy(@RequestParam String type,
                                        @RequestParam Double paymentAmount,
                                        RedirectAttributes redirectAttributes) {
        DisbursementPolicy newDisbursementPolicy = new DisbursementPolicy(type, paymentAmount);
        disbursementPolicyDAO.save(newDisbursementPolicy);

        redirectAttributes.addFlashAttribute("message", "Disbursement policy added successfully!");
        return "redirect:/disbursementPolicies";
    }

    @PostMapping("/editDisbursementPolicy")
    public String editDisbursementPolicy(@RequestParam Long id,
                                         @RequestParam String type,
                                         @RequestParam Double paymentAmount,
                                         RedirectAttributes redirectAttributes) {
        DisbursementPolicy policy = disbursementPolicyDAO.getById(id);

        if (policy == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to find disbursement policy with ID = " + id);
            return "redirect:/disbursementPolicies";
        }

        policy.setType(type);
        policy.setPaymentAmount(paymentAmount);
        disbursementPolicyDAO.update(policy);

        redirectAttributes.addFlashAttribute("message", "Disbursement policy updated successfully!");
        return "redirect:/disbursementPolicies";
    }

    @PostMapping("/deleteDisbursementPolicy")
    public String deleteDisbursementPolicy(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        DisbursementPolicy disbursementPolicy = disbursementPolicyDAO.getById(id);

        if (disbursementPolicy == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to delete disbursement policy with ID = " + id);
        } else {
            disbursementPolicyDAO.delete(disbursementPolicy);
            redirectAttributes.addFlashAttribute("message", "Disbursement policy deleted successfully!");
        }

        return "redirect:/disbursementPolicies";
    }
}
