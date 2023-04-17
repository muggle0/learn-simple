local value = 0;
redis.log(redis.LOG_WARNING, value);
local curScore = redis.call('zscore', KEYS[1], ARGV[1]);
if curScore then
    value = math.floor(curScore);
end
local score = (1 - tonumber('0.' .. ARGV[2])) + ARGV[3] + value;
redis.call('zadd', KEYS[1], score, ARGV[1]);