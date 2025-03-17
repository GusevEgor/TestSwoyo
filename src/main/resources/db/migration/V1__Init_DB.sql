
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL,
    name VARCHAR NOT NULL UNIQUE,
    CONSTRAINT pk_users primary key (id)
);

CREATE TABLE IF NOT EXISTS topics (
    id BIGSERIAL,
    title VARCHAR NOT NULL UNIQUE,
    CONSTRAINT pk_topics primary key (id)
);

CREATE TABLE IF NOT EXISTS votes (
    id BIGSERIAL,
    title VARCHAR NOT NULL UNIQUE,
    description VARCHAR NOT NULL,
    topic_id BIGINT,
    user_id BIGINT,
    CONSTRAINT pk_votes primary key (id),
    CONSTRAINT fk_votes_topics foreign key (topic_id) references topics (id),
    CONSTRAINT fk_topics_users foreign key (user_id) references users (id)
);

CREATE TABLE IF NOT EXISTS answers (
    id BIGSERIAL,
    vote_id BIGINT,
    title_of_answer VARCHAR NOT NULL,
    number_of_votes INTEGER DEFAULT 0,
    CONSTRAINT pk_voices primary key (id),
    CONSTRAINT fk_voices_votes foreign key (vote_id) references votes (id) ON DELETE CASCADE
);

