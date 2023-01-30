package account.data.entities;

import account.utility.ActionType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class SecurityEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    @Enumerated
    private ActionType action;
    private String subject;
    private String object;
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e){
            return "SecurityEventEntity{" +
                    "eventId=" + id +
                    ", date=" + date +
                    ", action=" + action +
                    ", subject='" + subject + '\'' +
                    ", object='" + object + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }
}
