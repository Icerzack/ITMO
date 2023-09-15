package domainModel;

public class Girl extends Person {
    private boolean hatesHim;
    private boolean isBusy;

    public Girl(String name, int age, Gender gender) {
        super(name, age, gender);
        this.hatesHim = true;
        this.isBusy = false;
    }

    public void hatesGuy(Stranger guy) {
        System.out.println(this.getName() + " ненавидит " + guy.getName());
        this.hatesHim = true;
    }

    public void payAttentionToTransformation() {
        System.out.println(this.getName() + " обращает внимание на трасформацию");
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean isHatingSomeone(){
        return hatesHim;
    }
}