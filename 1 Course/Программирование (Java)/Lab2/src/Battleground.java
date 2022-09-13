import ru.ifmo.se.pokemon.*;

public class Battleground {

    public static void main(String[] args) {
        Battle field = new Battle();
        field.addAlly(new Azumarill("Johnny Depp", 24));
        field.addAlly(new Azurill("Brad Pitt", 12));
        field.addAlly(new Inkay("Leonardo DiCaprio", 15));
        field.addFoe(new Dedenne("Kevin Spacey", 40));
        field.addFoe(new Malamar("Jason Statham", 15));
        field.addFoe(new Marill("Bruce Willis", 28));
        field.go();
    }
}
