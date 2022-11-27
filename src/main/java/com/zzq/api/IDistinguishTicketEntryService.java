package com.zzq.api;

import com.zzq.entity.Entry;
import com.zzq.model.TicketEntity;

public interface IDistinguishTicketEntryService {
    Entry distinguishEntity(String distinguishMsg, String distinguishMsg2);

}
