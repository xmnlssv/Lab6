package utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

/**
 * Represents date adapter.
 */
public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public String marshal(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toString();
    }

    @Override
    public ZonedDateTime unmarshal(String v) {
        return ZonedDateTime.parse(v);
    }
}