public class Penguin implements java.io.Serializable {

    //it's important that you make whatever you're saving, as well as any objects within it, serializable
    public double height;
    public String species;
    public int age;

    public Penguin(double height, int age, String species){
        this.height = height;
        this.species = species;
        this.age = age;
    }

    public void print(){
        System.out.println("Species: "+species);
        System.out.println("Height: "+height);
        System.out.println("age: "+age);
        System.out.println();
    }

}
