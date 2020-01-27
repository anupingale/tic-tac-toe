(ns tic-tac-toe.core)

(def winning-combination [#{1 2 3} #{4 5 6} #{7 8 9} #{1 5 9} #{3 5 7} #{1 4 7} #{2 5 8} #{3 6 9}])

(defn has-won? [combination] (some #(clojure.set/subset? % combination) winning-combination))

(defn swap [players] {:next-player (:current-player players) :current-player (:next-player players)})

(defn get-integer-input [] (do (println "Enter the value ") (Integer/parseInt (read-line))))

(defn validate-move [previous-moves current-move] (not (contains? previous-moves current-move)))

(defn get-valid-move [state] (loop [current-move (get-integer-input)]
                               (if (validate-move (clojure.set/union (:moves (:next-player state)) (:moves (:current-player state))) current-move)
                                 current-move
                                 (do (println "Invalid move. Please try with something else") (recur (get-integer-input))))))

(defn make-move [state] (update-in state [:current-player :moves] #(conj % (get-valid-move state))))

(defn print-move [player-1-details, player-2-details, move]
  (cond
    ((:moves player-1-details) move) (get player-1-details :symbol)
    ((:moves player-2-details) move) (get player-2-details :symbol)
    :else " "))

(defn print-board [game] (println (clojure.string/join "\n" (map (partial clojure.string/join " | ") (partition 3 (map (partial print-move (:next-player game) (:current-player game)) (range 1 10)))))))
(defn display-result [player] (str "Winner of the game is : " (:name player)))

(defn game [state] (loop [state state]
                     (do (print-board state)
                         (if (has-won? (:moves (:next-player state)))
                           (display-result (:next-player state))
                           (recur (swap (make-move state)))))))

(game {:next-player {:name "sai", :moves #{}, :symbol "X"} :current-player {:name "anu", :moves #{}, :symbol "O"}})

