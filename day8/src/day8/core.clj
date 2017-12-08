(ns day8.core
  "AOC 2017 Day 8"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn parse-action
  [a]
  (let [action (string/trim a)]
    (cond
      (= action "inc") +
      (= action "dec") -
      :else nil)))

(defn parse-test-action
  [a]
  (let [action (string/trim a)]
    (cond
      (= action ">") >
      (= action "<") <
      (= action ">=") >=
      (= action "<=") <=
      (= action "==") =
      (= action "!=") not=
      :else nil)))

(defn parse-instruction
  [input]
  (let [pieces (string/split input #"\s+")]
    {:reg (nth pieces 0)
     :action (parse-action (nth pieces 1))
     :action-arg (Integer/parseInt (nth pieces 2))
     :test-reg (nth pieces 4)
     :test-action (parse-test-action (nth pieces 5))
     :test-arg (Integer/parseInt (nth pieces 6))}))

(defn test-instruction
  [inst reg]
  ((:test-action inst) (get reg (:test-reg inst) 0) (:test-arg inst)))

(defn action
  [inst reg]
  (let [curr-val (get reg (:reg inst) 0)]
    (assoc reg (:reg inst) ((:action inst) curr-val (:action-arg inst)))))

(defn my-eval
  "Evaluate one instruction"
  [inst reg]
  (if (test-instruction inst reg)
    (action inst reg)
    reg))

(defn exec
  "Execute the given instructions"
  [i r]
  (loop [insts i
         reg r]
    (if (empty? insts)
      reg
      (let [new-reg (my-eval (first insts) reg)]
        (recur (rest insts) new-reg)))))

(defn run
  "Parse and run the program, return register map"
  [input]
  (let [lines (string/split input #"\n")
        insts (map parse-instruction lines)]
    (exec insts {})))

(defn max-register
  "Return the largest value in any register"
  [reg]
  (-> reg
    vals
    sort
    last))

(defn -main
  "AOC Day 8 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (let [reg (run input)
            max-val (max-register reg)]
        (println "Part 1:" max-val)
        (println "Part 2: TBD")))))
