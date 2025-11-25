-- Heroes
INSERT INTO Hero (id, name, description, level, strength, agility, intelligence, imageUrl, specialPower) VALUES 
(nextval('Hero_SEQ'), 'Captain Thunder', 'A legendary hero from a distant planet, gifted with the power of lightning. With unwavering courage, Captain Thunder protects the innocent and fights for justice.', 10, 95, 80, 85, null, 'Thunder Strike'),
(nextval('Hero_SEQ'), 'Quantum Shield', 'Born with extraordinary gifts of energy manipulation. Quantum Shield uses their quantum powers to defend the weak and uphold the law.', 8, 70, 90, 95, null, 'Quantum Barrier'),
(nextval('Hero_SEQ'), 'Blade Storm', 'Trained by mystical warriors in the ancient arts. Blade Storm is a master of combat and strategy, dedicated to protecting humanity.', 9, 88, 92, 75, null, 'Tempest Slash'),
(nextval('Hero_SEQ'), 'Solar Phoenix', 'Empowered by cosmic energy, Solar Phoenix can harness the power of the sun. This brave hero fights against evil with fiery determination.', 11, 93, 85, 88, null, 'Solar Flare');

-- Villains
INSERT INTO Villain (id, name, description, level, strength, agility, intelligence, imageUrl, evilPlan) VALUES 
(nextval('Villain_SEQ'), 'Shadow Master', 'The sinister adversary who wields the power of darkness. Currently seeking world domination, Shadow Master will stop at nothing to achieve their dark ambitions.', 10, 90, 75, 92, null, 'Control all governments'),
(nextval('Villain_SEQ'), 'Doctor Chaos', 'A diabolical genius plotting revenge against society. With technological supremacy, Doctor Chaos creates havoc wherever they go.', 9, 70, 60, 98, null, 'Build a doomsday device'),
(nextval('Villain_SEQ'), 'Venom Queen', 'The ruthless villain with toxic abilities. Reshaping reality to their twisted vision, Venom Queen shows no mercy to heroes.', 11, 85, 88, 83, null, 'Enslave humanity'),
(nextval('Villain_SEQ'), 'Ice King', 'Malevolent master of winter magic. Currently harvesting cosmic power, Ice King seeks to freeze the world in eternal winter.', 8, 82, 70, 90, null, 'Freeze time itself');
