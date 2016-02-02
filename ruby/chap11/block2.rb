ary = ["a", "b", "c"]
ary.each {|obj| p obj}
ary.each_with_index { |obj, idx|
  p [obj, idx]
}
