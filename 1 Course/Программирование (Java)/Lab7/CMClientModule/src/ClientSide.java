public class ClientSide {
    public static void main(String[] args) {
        System.out.println("Запуск клиентского модуля.\nПодключение к серверу ...");
        ClientConnection connection = new ClientConnection();
        connection.work();
        }
}