package com.google.gson.internal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Comparator NATURAL_ORDER = new Comparator() {
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    private final boolean allowNullValues;
    private final Comparator<? super K> comparator;
    private EntrySet entrySet;
    final Node header;
    private KeySet keySet;
    int modCount;
    Node root;
    int size;

    static {
        Class<LinkedTreeMap> cls = LinkedTreeMap.class;
    }

    public LinkedTreeMap() {
        this(NATURAL_ORDER, true);
    }

    public LinkedTreeMap(boolean z) {
        this(NATURAL_ORDER, z);
    }

    public LinkedTreeMap(Comparator<? super K> comparator2, boolean z) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = comparator2 == null ? NATURAL_ORDER : comparator2;
        this.allowNullValues = z;
        this.header = new Node(z);
    }

    public int size() {
        return this.size;
    }

    public Object get(Object obj) {
        Node findByObject = findByObject(obj);
        if (findByObject != null) {
            return findByObject.value;
        }
        return null;
    }

    public boolean containsKey(Object obj) {
        return findByObject(obj) != null;
    }

    public Object put(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        } else if (obj2 != null || this.allowNullValues) {
            Node find = find(obj, true);
            Object obj3 = find.value;
            find.value = obj2;
            return obj3;
        } else {
            throw new NullPointerException("value == null");
        }
    }

    public void clear() {
        this.root = null;
        this.size = 0;
        this.modCount++;
        Node node = this.header;
        node.prev = node;
        node.next = node;
    }

    public Object remove(Object obj) {
        Node removeInternalByKey = removeInternalByKey(obj);
        if (removeInternalByKey != null) {
            return removeInternalByKey.value;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Node find(Object obj, boolean z) {
        int i;
        Node node;
        Node node2;
        Comparator<? super K> comparator2 = this.comparator;
        Node node3 = this.root;
        if (node3 != null) {
            Comparable comparable = comparator2 == NATURAL_ORDER ? (Comparable) obj : null;
            while (true) {
                if (comparable != null) {
                    i = comparable.compareTo(node3.key);
                } else {
                    i = comparator2.compare(obj, node3.key);
                }
                if (i == 0) {
                    return node3;
                }
                if (i < 0) {
                    node2 = node3.left;
                } else {
                    node2 = node3.right;
                }
                if (node2 == null) {
                    break;
                }
                node3 = node2;
            }
        } else {
            i = 0;
        }
        Node node4 = node3;
        if (!z) {
            return null;
        }
        Node node5 = this.header;
        if (node4 != null) {
            node = new Node(this.allowNullValues, node4, obj, node5, node5.prev);
            if (i < 0) {
                node4.left = node;
            } else {
                node4.right = node;
            }
            rebalance(node4, true);
        } else if (comparator2 != NATURAL_ORDER || (obj instanceof Comparable)) {
            node = new Node(this.allowNullValues, node4, obj, node5, node5.prev);
            this.root = node;
        } else {
            throw new ClassCastException(obj.getClass().getName() + " is not Comparable");
        }
        this.size++;
        this.modCount++;
        return node;
    }

    /* access modifiers changed from: package-private */
    public Node findByObject(Object obj) {
        if (obj != null) {
            try {
                return find(obj, false);
            } catch (ClassCastException unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Node findByEntry(Map.Entry entry) {
        Node findByObject = findByObject(entry.getKey());
        if (findByObject == null || !equal(findByObject.value, entry.getValue())) {
            return null;
        }
        return findByObject;
    }

    private boolean equal(Object obj, Object obj2) {
        return Objects.equals(obj, obj2);
    }

    /* access modifiers changed from: package-private */
    public void removeInternal(Node node, boolean z) {
        int i;
        if (z) {
            Node node2 = node.prev;
            node2.next = node.next;
            node.next.prev = node2;
        }
        Node node3 = node.left;
        Node node4 = node.right;
        Node node5 = node.parent;
        int i2 = 0;
        if (node3 == null || node4 == null) {
            if (node3 != null) {
                replaceInParent(node, node3);
                node.left = null;
            } else if (node4 != null) {
                replaceInParent(node, node4);
                node.right = null;
            } else {
                replaceInParent(node, (Node) null);
            }
            rebalance(node5, false);
            this.size--;
            this.modCount++;
            return;
        }
        Node last = node3.height > node4.height ? node3.last() : node4.first();
        removeInternal(last, false);
        Node node6 = node.left;
        if (node6 != null) {
            i = node6.height;
            last.left = node6;
            node6.parent = last;
            node.left = null;
        } else {
            i = 0;
        }
        Node node7 = node.right;
        if (node7 != null) {
            i2 = node7.height;
            last.right = node7;
            node7.parent = last;
            node.right = null;
        }
        last.height = Math.max(i, i2) + 1;
        replaceInParent(node, last);
    }

    /* access modifiers changed from: package-private */
    public Node removeInternalByKey(Object obj) {
        Node findByObject = findByObject(obj);
        if (findByObject != null) {
            removeInternal(findByObject, true);
        }
        return findByObject;
    }

    private void replaceInParent(Node node, Node node2) {
        Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 == null) {
            this.root = node2;
        } else if (node3.left == node) {
            node3.left = node2;
        } else {
            node3.right = node2;
        }
    }

    private void rebalance(Node node, boolean z) {
        while (node != null) {
            Node node2 = node.left;
            Node node3 = node.right;
            int i = 0;
            int i2 = node2 != null ? node2.height : 0;
            int i3 = node3 != null ? node3.height : 0;
            int i4 = i2 - i3;
            if (i4 == -2) {
                Node node4 = node3.left;
                Node node5 = node3.right;
                int i5 = node5 != null ? node5.height : 0;
                if (node4 != null) {
                    i = node4.height;
                }
                int i6 = i - i5;
                if (i6 == -1 || (i6 == 0 && !z)) {
                    rotateLeft(node);
                } else {
                    rotateRight(node3);
                    rotateLeft(node);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                Node node6 = node2.left;
                Node node7 = node2.right;
                int i7 = node7 != null ? node7.height : 0;
                if (node6 != null) {
                    i = node6.height;
                }
                int i8 = i - i7;
                if (i8 == 1 || (i8 == 0 && !z)) {
                    rotateRight(node);
                } else {
                    rotateLeft(node2);
                    rotateRight(node);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                node.height = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                node.height = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            node = node.parent;
        }
    }

    private void rotateLeft(Node node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node3.left;
        Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        int i = 0;
        int max = Math.max(node2 != null ? node2.height : 0, node4 != null ? node4.height : 0) + 1;
        node.height = max;
        if (node5 != null) {
            i = node5.height;
        }
        node3.height = Math.max(max, i) + 1;
    }

    private void rotateRight(Node node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node2.left;
        Node node5 = node2.right;
        node.left = node5;
        if (node5 != null) {
            node5.parent = node;
        }
        replaceInParent(node, node2);
        node2.right = node;
        node.parent = node2;
        int i = 0;
        int max = Math.max(node3 != null ? node3.height : 0, node5 != null ? node5.height : 0) + 1;
        node.height = max;
        if (node4 != null) {
            i = node4.height;
        }
        node2.height = Math.max(max, i) + 1;
    }

    public Set entrySet() {
        EntrySet entrySet2 = this.entrySet;
        if (entrySet2 != null) {
            return entrySet2;
        }
        EntrySet entrySet3 = new EntrySet();
        this.entrySet = entrySet3;
        return entrySet3;
    }

    public Set keySet() {
        KeySet keySet2 = this.keySet;
        if (keySet2 != null) {
            return keySet2;
        }
        KeySet keySet3 = new KeySet();
        this.keySet = keySet3;
        return keySet3;
    }

    final class Node implements Map.Entry {
        final boolean allowNullValue;
        int height;
        final Object key;
        Node left;
        Node next;
        Node parent;
        Node prev;
        Node right;
        Object value;

        Node(boolean z) {
            this.key = null;
            this.allowNullValue = z;
            this.prev = this;
            this.next = this;
        }

        Node(boolean z, Node node, Object obj, Node node2, Node node3) {
            this.parent = node;
            this.key = obj;
            this.allowNullValue = z;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }

        public Object getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }

        public Object setValue(Object obj) {
            if (obj != null || this.allowNullValue) {
                Object obj2 = this.value;
                this.value = obj;
                return obj2;
            }
            throw new NullPointerException("value == null");
        }

        public boolean equals(Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                Object obj2 = this.key;
                if (obj2 != null ? obj2.equals(entry.getKey()) : entry.getKey() == null) {
                    Object obj3 = this.value;
                    if (obj3 == null) {
                        if (entry.getValue() == null) {
                            return true;
                        }
                    } else if (obj3.equals(entry.getValue())) {
                        return true;
                    }
                }
            }
            return false;
        }

        public int hashCode() {
            Object obj = this.key;
            int i = 0;
            int hashCode = obj == null ? 0 : obj.hashCode();
            Object obj2 = this.value;
            if (obj2 != null) {
                i = obj2.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public Node first() {
            Node node = this.left;
            while (true) {
                Node node2 = node;
                Node node3 = this;
                this = node2;
                if (this == null) {
                    return node3;
                }
                node = this.left;
            }
        }

        public Node last() {
            Node node = this.right;
            while (true) {
                Node node2 = node;
                Node node3 = this;
                this = node2;
                if (this == null) {
                    return node3;
                }
                node = this.right;
            }
        }
    }

    abstract class LinkedTreeMapIterator implements Iterator {
        int expectedModCount;
        Node lastReturned = null;
        Node next;

        LinkedTreeMapIterator() {
            this.next = LinkedTreeMap.this.header.next;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }

        public final boolean hasNext() {
            return this.next != LinkedTreeMap.this.header;
        }

        /* access modifiers changed from: package-private */
        public final Node nextNode() {
            Node node = this.next;
            LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
            if (node == linkedTreeMap.header) {
                throw new NoSuchElementException();
            } else if (linkedTreeMap.modCount == this.expectedModCount) {
                this.next = node.next;
                this.lastReturned = node;
                return node;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public final void remove() {
            Node node = this.lastReturned;
            if (node != null) {
                LinkedTreeMap.this.removeInternal(node, true);
                this.lastReturned = null;
                this.expectedModCount = LinkedTreeMap.this.modCount;
                return;
            }
            throw new IllegalStateException();
        }
    }

    class EntrySet extends AbstractSet {
        EntrySet() {
        }

        public int size() {
            return LinkedTreeMap.this.size;
        }

        public Iterator iterator() {
            return new LinkedTreeMapIterator() {
                {
                    LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
                }

                public Map.Entry next() {
                    return nextNode();
                }
            };
        }

        public boolean contains(Object obj) {
            return (obj instanceof Map.Entry) && LinkedTreeMap.this.findByEntry((Map.Entry) obj) != null;
        }

        public boolean remove(Object obj) {
            Node findByEntry;
            if (!(obj instanceof Map.Entry) || (findByEntry = LinkedTreeMap.this.findByEntry((Map.Entry) obj)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(findByEntry, true);
            return true;
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet {
        KeySet() {
        }

        public int size() {
            return LinkedTreeMap.this.size;
        }

        public Iterator iterator() {
            return new LinkedTreeMapIterator() {
                {
                    LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
                }

                public Object next() {
                    return nextNode().key;
                }
            };
        }

        public boolean contains(Object obj) {
            return LinkedTreeMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return LinkedTreeMap.this.removeInternalByKey(obj) != null;
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Deserialization is unsupported");
    }
}
