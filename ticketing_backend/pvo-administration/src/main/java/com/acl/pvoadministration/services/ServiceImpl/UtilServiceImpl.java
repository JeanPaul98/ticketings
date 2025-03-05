package com.acl.pvoadministration.services.ServiceImpl;

import com.acl.pvoadministration.model.Paiement;
import com.acl.pvoadministration.services.UtilService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UtilServiceImpl implements UtilService {
    @Override
    public boolean isTicketValid(Paiement payment) {
        if (payment == null) {
            return false;
        }

        Date dateExpiration = payment.getDateExpiration();
        Calendar calendarExpiration = Calendar.getInstance();
        calendarExpiration.setTime(dateExpiration);
        int expirationDay = calendarExpiration.get(Calendar.DAY_OF_YEAR);

        Calendar calendarNow = Calendar.getInstance();
        int nowDay = calendarNow.get(Calendar.DAY_OF_YEAR);

        return nowDay < expirationDay;
    }
}
