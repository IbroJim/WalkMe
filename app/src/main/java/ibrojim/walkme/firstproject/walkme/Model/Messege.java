package ibrojim.walkme.firstproject.walkme.Model;

public class Messege {


    public String message, type,from;
    public boolean seen;
    public  long time;

    public Messege(String message, boolean seen, long time, String type) {
        this.message = message;
        this.seen = seen;
        this.time = time;
        this.type = type;
    }

    public Messege(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessege() {
        return message;
    }

    public boolean getSeen() {
        return seen;
    }

    public long getTime() {
        return time;
    }

    public String getType() {
        return type;
    }


    public void setMessege(String messege) {
        this.message = messege;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Messege() {
    }
}
