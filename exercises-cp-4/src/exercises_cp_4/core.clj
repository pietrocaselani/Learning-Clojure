(ns exercises-cp-4.core
  (:gen-class))

(defn create-string
  ([] (str "create-string " "invoked with zero args"))
  ([n] (str "create-string invoked with " n))
  ([n & rest] (str "create-string invoked with first arg " n " and the rest is " rest)))

(defn create-vector [count]
  (vector "1" "2" 3 count))

(defn create-list [count]
  (list "1" "2" 3 count))

(defn create-hash-map []
  (hash-map :a "A" :b "B" :c "C"))

(defn create-hash-set []
  (hash-set :a "A" :b "B" :c "C" "A"))

(defn sum-100 [n]
  (+ n 100))

(defn dec-maker [number]
  (fn [n]
    (- n number)))

(def dec9 (dec-maker 9))

(defn mapset [f elements]
  (set (map f elements)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (create-string))
  (println (create-string 1))
  (println (create-string 1 2 3 4))
  (println (create-vector 10))
  (println (create-list 11))
  (println (create-hash-map))
  (println (create-hash-set))
  (println (sum-100 100))
  (println (dec9 10))
  (println (dec9 9))
  (println (dec9 8))
  (println (mapset inc [1 1 2 2])))
