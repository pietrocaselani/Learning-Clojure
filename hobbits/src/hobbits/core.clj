(ns hobbits.core
  (:gen-class))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])
(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a sequence of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn better-symmetrize-body-parts
  "Expects a sequence of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])

(def n 0)

(defn print-empty-lines [count]
  (loop [index 0]
    (println)
    (if (< index count)
      (recur (inc index)))))

(defn print-title [title]
  (println (str ">>> " title)))

(defn let-examples []
  (print-title "let examples")
  (let [x 3]
    (println x))
  (let [dalmatians (take 2 dalmatian-list)]
    (println dalmatians))
  (let [n 1]                                                ; let introduces new scope. n will 1 instead of 0
    (println n))
  (let [[pongo & other-dogs] dalmatian-list]
    (println [pongo other-dogs])))

(defn loop-example-1 []
  (print-title "Loop Example 1")
  (loop [iteration 0]
    (println (str "Iteration " iteration))
    (if (> iteration 3)
      (println "Goodbye!")
      (recur (inc iteration)))))

(defn recurisive-loop-example
  ([] (recurisive-loop-example 0))
  ([iteration]
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye")
     (recurisive-loop-example (inc iteration)))))

(defn regex-example []
  (print-title "Regex example")
  (println (re-find #"^left-" "left-eye"))
  (println (re-find #"^left-" "cleft-eye"))
  (println (re-find #"^left-" "pc")))

(defn reduce-examples []
  (print-title "Reduce examples")
  (println (reduce + [1 2 3 4]))
  (println (reduce + 15 [1 2 3 4])))

(defn my-reduce
  ([f initial acc]
   (loop [result initial
          remaining acc]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(defn reduce-anon-function []
  (my-reduce (fn [acc item]
               (str acc item)) [1 2 3]))

(defn reduce-anon-function-2 []
  (my-reduce #(str %1 %2) [3 2 1]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-title "Hello, World!")
  (print-empty-lines 2)
  (println (symmetrize-body-parts asym-hobbit-body-parts))
  (print-empty-lines 2)
  (let-examples)
  (print-empty-lines 2)
  (loop-example-1)
  (print-empty-lines 2)
  (print-title "Recursive loop")
  (recurisive-loop-example)
  (print-empty-lines 2)
  (regex-example)
  (println (matching-part {:name "left-eye" :size 1}))
  (println (matching-part {:name "head" :size 1}))
  (print-empty-lines 2)
  (reduce-examples)
  (println (my-reduce + [1 2 3]))
  (println (reduce-anon-function))
  (println (reduce-anon-function-2))
  (print-empty-lines 2)
  (println (better-symmetrize-body-parts asym-hobbit-body-parts))
  (println (str "Hitting " (hit asym-hobbit-body-parts)))
  (println (str "Hitting " (hit asym-hobbit-body-parts)))
  (println (str "Hitting " (hit asym-hobbit-body-parts))))