import ru.ifmo.se.pokemon.*;

public class Azurill extends Pokemon {
    public Azurill(String name, int lvl){
        super(name, lvl);
        setStats(50, 20, 40, 20, 40, 20);
        setType(Type.NORMAL, Type.FAIRY);
        setMove(new Confide(), new Scald());
    }
}
