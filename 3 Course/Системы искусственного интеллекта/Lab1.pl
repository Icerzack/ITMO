% some facts
% 1st -> 2nd gen
parent(terra, caelus).
% 2nd -> 3d gen
parent(terra, themis).
parent(terra, moneta).
parent(terra, hyperion).
parent(terra, theia).
parent(terra, crius).
parent(terra, oceanus).
parent(terra, tethys).
parent(terra, japetus).
parent(terra, saturn).
parent(terra, opis).
parent(terra, polus).
parent(terra, phoebe).
parent(caelus, themis).
parent(caelus, moneta).
parent(caelus, hyperion).
parent(caelus, theia).
parent(caelus, crius).
parent(caelus, oceanus).
parent(caelus, tethys).
parent(caelus, japetus).
parent(caelus, saturn).
parent(caelus, opis).
parent(caelus, polus).
parent(caelus, phoebe).
parent(caelus, venus).

% 3d -> 4th gen
parent(moneta, sol).
parent(moneta, aurora).
parent(moneta, luna).
parent(hyperion, sol).
parent(hyperion, aurora).
parent(hyperion, luna).
parent(oceanus, pleione).
parent(tethys, pleione).
parent(japetus, atlas).
parent(japetus, prometheus).
parent(japetus, epimetheus).
parent(saturn, juno).
parent(saturn, jupiter).
parent(saturn, ceres).
parent(saturn, pluto).
parent(saturn, neptune).
parent(saturn, vesta).
parent(opis, juno).
parent(opis, jupiter).
parent(opis, ceres).
parent(opis, pluto).
parent(opis, neptune).
parent(opis, vesta).
parent(polus, latona).
parent(phoebe, latona).
% 4th -> 5th gen
parent(atlas, maia).
parent(pleione, maia).
parent(jupiter, minerva).
parent(jupiter, mercury).
parent(jupiter, bacchus).
parent(jupiter, apollo).
parent(jupiter, diana).
parent(jupiter, proserpina).
parent(jupiter, vulcan).
parent(jupiter, mars).
parent(juno, vulcan).
parent(juno, mars).
parent(maia, mercury).
parent(semele, bacchus).
parent(latona, apollo).
parent(latona, diana).
parent(ceres, proserpina).
% 5th -> 6th gen
parent(mercury, faunus).
parent(penelope, faunus).
parent(mars, pavor).
parent(mars, formido).
parent(mars, cupid).
parent(mars, himerus).
parent(mars, romulus).
parent(mars, remus).
parent(venus, pavor).
parent(venus, formido).
parent(venus, cupid).
parent(venus, himerus).
parent(rhea_silvia, romulus).
parent(rhea_silvia, remus).
% Муж. боги
male(caelus).
male(hyperion).
male(crius).
male(sol).
male(oceanus).
male(japetus).
male(atlas).
male(prometheus).
male(epimetheus).
male(saturn).
male(polus).
male(jupiter).
male(mercury).
male(bacchus).
male(apollo).
male(vulcan).
male(mars).
male(faunus).
male(pluto).
male(pavor).
male(formido).
male(cupid).
male(himerus).
male(janus).
male(romulus).
male(remus).
male(neptune).
% Жен. боги
female(terra).
female(themis).
female(moneta).
female(theia).
female(aurora).
female(luna).
female(tethys).
female(pleione).
female(opis).
female(phoebe).
female(juno).
female(maia).
female(semele).
female(latona).
female(ceres).
female(minerva).
female(penelope).
female(diana).
female(venus).
female(proserpina).
female(rhea_silvia).
female(vesta).
% Женатые
married(terra, caelsus).
married(caelsus, terra).
married(moneta, hyperion).
married(hyperion, moneta).
married(oceanus, tethys).
married(tethys, oceanus).
married(saturn, opis).
married(opis, saturn).
married(polus, phoebe).
married(phoebe, polus).
married(atlas, pleione).
married(pleione, atlas).
married(jupiter, juno).
married(juno, jupiter).
married(jupiter, maia).
married(maia, jupiter).
married(jupiter, semele).
married(semele, jupiter).
married(jupiter, latona).
married(latona, jupiter).
married(jupiter, ceres).
married(ceres, jupiter).
married(mercury, penelope).
married(penelope, mercury).
married(mars, venus).
married(venus, mars).
married(mars, rhea_silvia).
married(rhea_silvia, mars).
% Правила
husband(A, B) :- (married(A, B); married(B, A)), male(A), female(B).
wife(A, B) :- (married(A, B); married(B, A)), female(A), male(B).
mother(A, B) :- parent(A, B), female(A).
father(A, B) :- parent(A, B), male(A).
grandfather(A, B) :- parent(A, C), parent(C, B), male(A).
grandmother(A, B) :- parent(A, C), parent(C, B), female(A).
son(A, B) :- parent(B, A), male(A).
daughter(A, B) :- parent(B, A), female(A).
sister(A, B) :- parent(C, A), parent(C, B), A \= B, female(A).
brother(A, B) :- parent(C, A), parent(C, B), A \= B, male(A).
ancestor(A, B) :- parent(A, B).
ancestor(A, B) :- parent(A, C), ancestor(C, B).