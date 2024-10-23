package managers;

import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    // Узел двусвязного списка
    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Node prev, Task task, Node next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }

    private Node head; // голова списка
    private Node tail; // хвост списка
    private final Map<Integer, Node> historyMap = new HashMap<>(); // Хранение id задачи -> узел списка

    @Override
    public void add(Task task) {
        // Если задача уже есть в истории, удаляем её
        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
        }
        // Добавляем задачу в конец списка
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        // Проход по двусвязному списку от головы до хвоста
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }

    // Метод для добавления задачи в конец списка
    private void linkLast(Task task) {
        Node newNode = new Node(tail, task, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode; // если список был пуст, это будет первый элемент
        }
        tail = newNode;
        historyMap.put(task.getId(), newNode); // Добавляем узел в HashMap
    }

    // Метод для удаления узла из списка
    private void removeNode(Node node) {
        if (node == null) {
            return;
        }

        Node prevNode = node.prev;
        Node nextNode = node.next;

        // Если узел не является головой списка
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode; // если это голова, двигаем её
        }

        // Если узел не является хвостом списка
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode; // если это хвост, двигаем его
        }

        // Удаляем узел из HashMap
        historyMap.remove(node.task.getId());
    }
}
