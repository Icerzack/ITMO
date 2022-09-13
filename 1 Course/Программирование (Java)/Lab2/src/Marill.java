import ru.ifmo.se.pokemon.*;

public class Marill extends Azurill {
    public Marill(String name, int lvl){
        super(name, lvl);
        setStats(70, 20, 50, 20, 50, 40);
        setType(Type.WATER, Type.FAIRY);
        setMove(new Confide(), new Scald(), new MuddyWater());
    }
}
