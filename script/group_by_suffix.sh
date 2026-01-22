for d in p*; do
  [ -d "$d" ] || continue

  last=${d: -1}          # last character
  [[ $last =~ [0-9] ]] || continue

  mkdir -p g$last
  mv "$d" g$last/
done
