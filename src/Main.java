import java.util.LinkedList;

class HashSet
{
    LinkedList<Integer>[] arr;
    private int size;
    private int capacity = 7;
    private double loadFactor = 0.75;


    public boolean search(Integer element)
    {
        return this.arr[hash(element)].contains(element);
    }
    public HashSet()
    {
        this.arr = new LinkedList[this.capacity];
        for(int i = 0; i < this.arr.length; ++i)
        {
            this.arr[i] = new LinkedList<Integer>();
        }
    }
    public int hash(Integer element)
    {
        return element % this.capacity;
    }

    public void insert(Integer element)
    {
        if(element == null)
        {
            throw new RuntimeException("Invalid");
        }
        LinkedList<Integer> list = this.arr[hash(element)];
        if(!list.contains(element))
        {
            list.add(element);
            ++this.size;
            if((double)(this.size) / this.capacity > this.loadFactor)
            {
                this.rehash();
            }
        }
    }


    public void delete(Integer element)
    {
        if(element == null)
        {
            throw new RuntimeException("Invalid");
        }
        LinkedList<Integer> list = this.arr[hash(element)];
        if(list.isEmpty() || !list.contains(element))
        {
            throw new RuntimeException("Invalid");
        }
        list.remove(element);
        --this.size;

        if((double)(this.size) / this.capacity < this.loadFactor)
        {
            this.shrink();
        }
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }


    public void shrink()
    {

        int newCapacity = (int) Math.ceil(this.size / this.loadFactor);
        while (!isPrime(newCapacity))
        {
            ++newCapacity;
        }
        this.capacity = newCapacity;
        LinkedList<Integer>[] arr1 = new LinkedList[newCapacity];
        for(int i = 0; i < newCapacity; ++i)
        {
            arr1[i] = new LinkedList<Integer>();
        }
        for(int i = 0; i < this.arr.length; ++i)
        {
            for(int j = 0; j < this.arr[i].size(); ++j)
            {
                arr1[hash(this.arr[i].get(j))].add(this.arr[i].get(j));
            }
        }
        this.arr = arr1;
    }
    public void rehash()
    {

        int newCapacity = this.capacity;
        while(true)
        {
            ++newCapacity;
            if (isPrime(newCapacity))
            {
                break;
            }
        }
        this.capacity = newCapacity;
        LinkedList<Integer>[] arr1 = new LinkedList[newCapacity];
        for(int i = 0; i < arr1.length; ++i)
        {
            arr1[i] = new LinkedList<Integer>();
        }

        for(int i = 0; i < this.arr.length; ++i)
        {
            for(int j = 0; j < this.arr[i].size(); ++j)
            {
                arr1[hash(this.arr[i].get(j))].add(this.arr[i].get(j));
            }
        }

        this.arr = arr1;
    }

}



public class Main
{
    public static void main(String[] args)
    {
        HashSet myHashSet = new HashSet();

        myHashSet.insert(1);
        myHashSet.insert(2);
        myHashSet.insert(3);

        System.out.println();
        printHashSet(myHashSet);

        System.out.println(myHashSet.search(2));

        myHashSet.delete(2);
        System.out.println();
        printHashSet(myHashSet);

        myHashSet.insert(9);
        System.out.println();
        printHashSet(myHashSet);
    }

    private static void printHashSet(HashSet hashSet)
    {
        for (int i = 0; i < hashSet.arr.length; ++i)
        {
            System.out.print("Bucket " + i + ": ");
            for (Integer element : hashSet.arr[i])
            {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

}